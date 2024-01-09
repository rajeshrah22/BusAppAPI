import React from 'react'
import Box from '@mui/material/Box'
import { useTheme } from '@emotion/react'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'

export function HeadingCard() {

  const theme = useTheme()
  return (
    <Paper
      sx={{
        backgroundColor: theme.palette.primary.main,
        height: "15%",
        mb: 2,
        borderRadius: 1,
      }}
      elevation={4}
    >
      <Box
        sx={{
          p: 2,
        }}
      >
        <Typography
          color="white"
          variant="h4"
        >Agencies</Typography>
      </Box>
      <Box
        sx={{
          p: 2,
        }}
      >
        <Typography
          color="white"
          variant="body1"
        >Select agency on map or in the list below</Typography>
      </Box>
    </Paper>
  )
}
